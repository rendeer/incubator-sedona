/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sedona.core.spatialOperator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.sedona.core.enums.FileDataSplitter;
import org.apache.sedona.core.enums.IndexType;
import org.apache.sedona.core.rangeJudgement.RangeFilter;
import org.apache.sedona.core.rangeJudgement.RangeFilterUsingIndex;
import org.apache.sedona.core.spatialRDD.RectangleRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc

/**
 * The Class RectangleRangeTest.
 */
public class RectangleRangeTest
{

    /**
     * The sc.
     */
    public static JavaSparkContext sc;

    /**
     * The prop.
     */
    static Properties prop;

    /**
     * The input.
     */
    static InputStream input;

    /**
     * The Input location.
     */
    static String InputLocation;

    /**
     * The offset.
     */
    static Integer offset;

    /**
     * The splitter.
     */
    static FileDataSplitter splitter;

    /**
     * The index type.
     */
    static IndexType indexType;

    /**
     * The num partitions.
     */
    static Integer numPartitions;

    /**
     * The query envelope.
     */
    static Envelope queryEnvelope;

    /**
     * The loop times.
     */
    static int loopTimes;

    /**
     * Once executed before all.
     */
    @BeforeClass
    public static void onceExecutedBeforeAll()
    {
        SparkConf conf = new SparkConf().setAppName("RectangleRange").setMaster("local[2]");
        sc = new JavaSparkContext(conf);
        Logger.getLogger("org").setLevel(Level.WARN);
        Logger.getLogger("akka").setLevel(Level.WARN);
        prop = new Properties();
        input = RectangleRangeTest.class.getClassLoader().getResourceAsStream("rectangle.test.properties");

        //Hard code to a file in resource folder. But you can replace it later in the try-catch field in your hdfs system.
        InputLocation = "file://" + RectangleRangeTest.class.getClassLoader().getResource("primaryroads.csv").getPath();

        offset = 0;
        splitter = null;
        indexType = null;
        numPartitions = 0;

        try {
            // load a properties file
            prop.load(input);
            // There is a field in the property file, you can edit your own file location there.
            // InputLocation = prop.getProperty("inputLocation");
            InputLocation = "file://" + RectangleRangeTest.class.getClassLoader().getResource(prop.getProperty("inputLocation")).getPath();
            offset = Integer.parseInt(prop.getProperty("offset"));
            splitter = FileDataSplitter.getFileDataSplitter(prop.getProperty("splitter"));
            indexType = IndexType.getIndexType(prop.getProperty("indexType"));
            numPartitions = Integer.parseInt(prop.getProperty("numPartitions"));
            queryEnvelope = new Envelope(-90.01, -80.01, 30.01, 40.01);
            loopTimes = 5;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Tear down.
     */
    @AfterClass
    public static void TearDown()
    {
        sc.stop();
    }

    /**
     * Test spatial range query.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSpatialRangeQuery()
            throws Exception
    {
        RectangleRDD spatialRDD = new RectangleRDD(sc, InputLocation, offset, splitter, true, StorageLevel.MEMORY_ONLY());
        for (int i = 0; i < loopTimes; i++) {
            long resultSize = RangeQuery.SpatialRangeQuery(spatialRDD, queryEnvelope, false, false).count();
            assertEquals(resultSize, 193);
        }
        assert RangeQuery.SpatialRangeQuery(spatialRDD, queryEnvelope, false, false).take(10).get(1).getUserData().toString() != null;
    }

    /**
     * Test spatial range query using index.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSpatialRangeQueryUsingIndex()
            throws Exception
    {
        RectangleRDD spatialRDD = new RectangleRDD(sc, InputLocation, offset, splitter, true, StorageLevel.MEMORY_ONLY());
        spatialRDD.buildIndex(IndexType.RTREE, false);
        for (int i = 0; i < loopTimes; i++) {
            long resultSize = RangeQuery.SpatialRangeQuery(spatialRDD, queryEnvelope, false, true).count();
            assertEquals(resultSize, 193);
        }
        assert RangeQuery.SpatialRangeQuery(spatialRDD, queryEnvelope, false, true).take(10).get(1).getUserData().toString() != null;
    }

    /**
     * Test spatial range query not using index and leftCoveredByRight is false.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSpatialRangeQueryLeftCoveredByRightFalse()
            throws Exception {
        RectangleRDD spatialRDD = new RectangleRDD(sc, InputLocation, offset, splitter, true, StorageLevel.MEMORY_ONLY());
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(queryEnvelope.getMinX(), queryEnvelope.getMinY());
        coordinates[1] = new Coordinate(queryEnvelope.getMinX(), queryEnvelope.getMaxY());
        coordinates[2] = new Coordinate(queryEnvelope.getMaxX(), queryEnvelope.getMaxY());
        coordinates[3] = new Coordinate(queryEnvelope.getMaxX(), queryEnvelope.getMinY());
        coordinates[4] = coordinates[0];
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon queryGeometry = geometryFactory.createPolygon(coordinates);
        spatialRDD.buildIndex(IndexType.RTREE, false);
        long useIndexResultSize = spatialRDD.indexedRawRDD.mapPartitions(new RangeFilterUsingIndex(queryGeometry, false, false)).count();
        for (int i = 0; i < loopTimes; i++) {
            long notUseIndexResultSize = spatialRDD.getRawSpatialRDD().filter(new RangeFilter(queryGeometry, false, false)).count();
            assertEquals(useIndexResultSize, notUseIndexResultSize);
        }
    }
}