## ST_Distance

Introduction: Return the Euclidean distance between A and B

Format: `ST_Distance (A:geometry, B:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Distance(polygondf.countyshape, polygondf.countyshape)
FROM polygondf
```

## ST_ConvexHull

Introduction: Return the Convex Hull of polgyon A

Format: `ST_ConvexHull (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_ConvexHull(polygondf.countyshape)
FROM polygondf
```

## ST_Envelope

Introduction: Return the envelop boundary of A

Format: `ST_Envelope (A:geometry)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT ST_Envelope(polygondf.countyshape)
FROM polygondf
```

## ST_Length

Introduction: Return the perimeter of A

Format: ST_Length (A:geometry)

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Length(polygondf.countyshape)
FROM polygondf
```

## ST_Area

Introduction: Return the area of A

Format: `ST_Area (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Area(polygondf.countyshape)
FROM polygondf
```

## ST_Centroid

Introduction: Return the centroid point of A

Format: `ST_Centroid (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Centroid(polygondf.countyshape)
FROM polygondf
```



## ST_Transform

Introduction:

Transform the Spatial Reference System / Coordinate Reference System of A, from SourceCRS to TargetCRS

!!!note
	By default, this function uses lat/lon order. You can use ==ST_FlipCoordinates== to swap X and Y.

!!!note
	If ==ST_Transform== throws an Exception called "Bursa wolf parameters required", you need to disable the error notification in ST_Transform. You can append a boolean value at the end.

Format: `ST_Transform (A:geometry, SourceCRS:string, TargetCRS:string ,[Optional] DisableError)`

Since: `v1.0.0`

Spark SQL example (simple):
```SQL
SELECT ST_Transform(polygondf.countyshape, 'epsg:4326','epsg:3857') 
FROM polygondf
```

Spark SQL example (with optional parameters):
```SQL
SELECT ST_Transform(polygondf.countyshape, 'epsg:4326','epsg:3857', false)
FROM polygondf
```

!!!note
	The detailed EPSG information can be searched on [EPSG.io](https://epsg.io/).

## ST_Intersection

Introduction: Return the intersection geometry of A and B

Format: `ST_Intersection (A:geometry, B:geometry)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT ST_Intersection(polygondf.countyshape, polygondf.countyshape)
FROM polygondf
```

## ST_IsValid

Introduction: Test if a geometry is well formed

Format: `ST_IsValid (A:geometry)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT ST_IsValid(polygondf.countyshape)
FROM polygondf
```

## ST_MakeValid

Introduction: Given an invalid polygon or multipolygon and removeHoles boolean flag,
 create a valid representation of the geometry.

Format: `ST_MakeValid (A:geometry, removeHoles:Boolean)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT geometryValid.polygon
FROM table
LATERAL VIEW ST_MakeValid(polygon, false) geometryValid AS polygon
```

!!!note
    Might return multiple polygons from a only one invalid polygon
    That's the reason why we need to use the LATERAL VIEW expression
    
!!!note
    Throws an exception if the geometry isn't polygon or multipolygon

## ST_PrecisionReduce

Introduction: Reduce the decimals places in the coordinates of the geometry to the given number of decimal places. The last decimal place will be rounded.

Format: `ST_PrecisionReduce (A:geometry, B:int)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT ST_PrecisionReduce(polygondf.countyshape, 9)
FROM polygondf
```
The new coordinates will only have 9 decimal places.

## ST_IsSimple

Introduction: Test if geometry's only self-intersections are at boundary points.

Format: `ST_IsSimple (A:geometry)`

Since: `v1.0.0`

Spark SQL example:

```SQL
SELECT ST_IsSimple(polygondf.countyshape)
FROM polygondf
```

## ST_Buffer

Introduction: Returns a geometry/geography that represents all points whose distance from this Geometry/geography is less than or equal to distance.

Format: `ST_Buffer (A:geometry, buffer: Double)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Buffer(polygondf.countyshape, 1)
FROM polygondf
```

## ST_AsText

Introduction: Return the Well-Known Text string representation of a geometry

Format: `ST_AsText (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_AsText(polygondf.countyshape)
FROM polygondf
```

## ST_AsGeoJSON

Introduction: Return the [GeoJSON](https://geojson.org/) string representation of a geometry

Format: `ST_AsGeoJSON (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_AsGeoJSON(polygondf.countyshape)
FROM polygondf
```

## ST_NPoints

Introduction: Return points of the geometry

Since: `v1.0.0`

Format: `ST_NPoints (A:geometry)`

```SQL
SELECT ST_NPoints(polygondf.countyshape)
FROM polygondf
```

## ST_SimplifyPreserveTopology

Introduction: Simplifies a geometry and ensures that the result is a valid geometry having the same dimension and number of components as the input,
              and with the components having the same topological relationship.

Since: `v1.0.0`

Format: `ST_SimplifyPreserveTopology (A:geometry, distanceTolerance: Double)`

```SQL
SELECT ST_SimplifyPreserveTopology(polygondf.countyshape, 10.0)
FROM polygondf
```

## ST_GeometryType

Introduction: Returns the type of the geometry as a string. EG: 'ST_Linestring', 'ST_Polygon' etc.

Format: `ST_GeometryType (A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_GeometryType(polygondf.countyshape)
FROM polygondf
```

## ST_LineMerge

Introduction: Returns a LineString formed by sewing together the constituent line work of a MULTILINESTRING.

!!!note
    Only works for MULTILINESTRING. Using other geometry will return a GEOMETRYCOLLECTION EMPTY. If the MultiLineString can't be merged, the original MULTILINESTRING is returned.

Format: `ST_LineMerge (A:geometry)`

Since: `v1.0.0`

```SQL
SELECT ST_LineMerge(geometry)
FROM df
```

## ST_Azimuth

Introduction: Returns Azimuth for two given points in radians null otherwise.

Format: `ST_Azimuth(pointA: Point, pointB: Point)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Azimuth(ST_POINT(0.0 25.0), ST_POINT(0.0 0.0))
```

Output: `3.141592653589793`

## ST_X

Introduction: Returns X Coordinate of given Point null otherwise.

Format: `ST_X(pointA: Point)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_X(ST_POINT(0.0 25.0))
```

Output: `0.0`

## ST_Y

Introduction: Returns Y Coordinate of given Point, null otherwise.

Format: `ST_Y(pointA: Point)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Y(ST_POINT(0.0 25.0))
```

Output: `25.0`

## ST_StartPoint

Introduction: Returns first point of given linestring.

Format: `ST_StartPoint(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_StartPoint(ST_GeomFromText('LINESTRING(100 150,50 60, 70 80, 160 170)'))
```

Output: `POINT(100 150)`

## ST_EndPoint

Introduction: Returns last point of given linestring.

Format: `ST_EndPoint(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_EndPoint(ST_GeomFromText('LINESTRING(100 150,50 60, 70 80, 160 170)'))
```

Output: `POINT(160 170)`

## ST_Boundary

Introduction: Returns the closure of the combinatorial boundary of this Geometry.

Format: `ST_Boundary(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Boundary(ST_GeomFromText('POLYGON((1 1,0 0, -1 1, 1 1))'))
```

Output: `LINESTRING (1 1, 0 0, -1 1, 1 1)`

## ST_ExteriorRing

Introduction: Returns a line string representing the exterior ring of the POLYGON geometry. Return NULL if the geometry is not a polygon.

Format: `ST_ExteriorRing(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_ExteriorRing(ST_GeomFromText('POLYGON((0 0 1, 1 1 1, 1 2 1, 1 1 1, 0 0 1))'))
```

Output: `LINESTRING (0 0, 1 1, 1 2, 1 1, 0 0)`

## ST_GeometryN

Introduction: Return the 1-based Nth geometry if the geometry is a GEOMETRYCOLLECTION, (MULTI)POINT, (MULTI)LINESTRING, MULTICURVE or (MULTI)POLYGON Otherwise, return null

Format: `ST_GeometryN(geom: geometry, n: Int)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_GeometryN(ST_GeomFromText('MULTIPOINT((1 2), (3 4), (5 6), (8 9))'), 1)
```

Output: `POINT (3 4)`

## ST_InteriorRingN

Introduction: Returns the Nth interior linestring ring of the polygon geometry. Returns NULL if the geometry is not a polygon or the given N is out of range

Format: `ST_InteriorRingN(geom: geometry, n: Int)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_InteriorRingN(ST_GeomFromText('POLYGON((0 0, 0 5, 5 5, 5 0, 0 0), (1 1, 2 1, 2 2, 1 2, 1 1), (1 3, 2 3, 2 4, 1 4, 1 3), (3 3, 4 3, 4 4, 3 4, 3 3))'), 0)
```

Output: `LINESTRING (1 1, 2 1, 2 2, 1 2, 1 1)`

## ST_Dump

Introduction: It expands the geometries. If the geometry is simple (Point, Polygon Linestring etc.) it returns the geometry
itself, if the geometry is collection or multi it returns record for each of collection components.
 
Format: `ST_Dump(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_Dump(ST_GeomFromText('MULTIPOINT ((10 40), (40 30), (20 20), (30 10))'))
```

Output: `[POINT (10 40), POINT (40 30), POINT (20 20), POINT (30 10)]`

## ST_DumpPoints

Introduction: Returns list of Points which geometry consists of.
 
Format: `ST_DumpPoints(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_DumpPoints(ST_GeomFromText('LINESTRING (0 0, 1 1, 1 0)')) 
```

Output: `[POINT (0 0), POINT (0 1), POINT (1 1), POINT (1 0), POINT (0 0)]`


## ST_IsClosed

Introduction: RETURNS true if the LINESTRING start and end point are the same.
 
Format: `ST_IsClosed(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_IsClosed(ST_GeomFromText('LINESTRING(0 0, 1 1, 1 0)'))
```

Output: `false`

## ST_NumInteriorRings

Introduction: RETURNS number of interior rings of polygon geometries.
 
Format: `ST_NumInteriorRings(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_NumInteriorRings(ST_GeomFromText('POLYGON ((0 0, 0 5, 5 5, 5 0, 0 0), (1 1, 2 1, 2 2, 1 2, 1 1))'))
```

Output: `1`

## ST_AddPoint

Introduction: RETURN Linestring with additional point at the given index, if position is not available the point will be added at the end of line.
 
Format: `ST_AddPoint(geom: geometry, point: geometry, position: integer)`

Format: `ST_AddPoint(geom: geometry, point: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_AddPoint(ST_GeomFromText("LINESTRING(0 0, 1 1, 1 0)"), ST_GeomFromText("Point(21 52)"), 1)

SELECT ST_AddPoint(ST_GeomFromText("Linestring(0 0, 1 1, 1 0)"), ST_GeomFromText("Point(21 52)"))
```

Output:
```
LINESTRING(0 0, 21 52, 1 1, 1 0)
LINESTRING(0 0, 1 1, 1 0, 21 52)
```


## ST_RemovePoint

Introduction: RETURN Line with removed point at given index, position can be omitted and then last one will be removed.
 
Format: `ST_RemovePoint(geom: geometry, position: integer)`

Format: `ST_RemovePoint(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_RemovePoint(ST_GeomFromText("LINESTRING(0 0, 1 1, 1 0)"), 1)
```

Output: `LINESTRING(0 0, 1 0)`

## ST_IsRing

Introduction: RETURN true if LINESTRING is ST_IsClosed and ST_IsSimple.
 
Format: `ST_IsRing(geom: geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_IsRing(ST_GeomFromText("LINESTRING(0 0, 0 1, 1 1, 1 0, 0 0)"))
```

Output: `true`

## ST_NumGeometries

Introduction: Returns the number of Geometries. If geometry is a GEOMETRYCOLLECTION (or MULTI*) return the number of geometries, for single geometries will return 1.

Format: `ST_NumGeometries (A:geometry)`

Since: `v1.0.0`

```SQL
SELECT ST_NumGeometries(df.geometry)
FROM df
```


## ST_FlipCoordinates

Introduction: Returns a version of the given geometry with X and Y axis flipped.

Format: `ST_FlipCoordinates(A:geometry)`

Since: `v1.0.0`

Spark SQL example:
```SQL
SELECT ST_FlipCoordinates(df.geometry)
FROM df
```

Input: `POINT (1 2)`

Output: `POINT (2 1)`


## ST_MinimumBoundingRadius

Introduction: Returns a struct containing the center point and radius of the smallest circle that contains a geometry.

Format: `ST_MinimumBoundingRadius(geom: geometry)`

Since: `v1.0.1`

Spark SQL example:
```SQL
SELECT ST_MinimumBoundingRadius(ST_GeomFromText('POLYGON((1 1,0 0, -1 1, 1 1))'))
```


## ST_MinimumBoundingCircle

Introduction: Returns the smallest circle polygon that contains a geometry.

Format: `ST_MinimumBoundingCircle(geom: geometry, [Optional] quadrantSegments:int)`

Since: `v1.0.1`

Spark SQL example:
```SQL
SELECT ST_MinimumBoundingCircle(ST_GeomFromText('POLYGON((1 1,0 0, -1 1, 1 1))'))
```

## ST_LineSubstring

Return a linestring being a substring of the input one starting and ending at the given fractions of total 2d length. Second and third arguments are Double values between 0 and 1. This only works with LINESTRINGs.

Format: `ST_LineSubstring(geom: LineString, startFraction: Double, endFraction: Double) `

Since: `v1.0.1`

Spark SQL example:
```SQL
SELECT ST_LineSubstring(df.geometry, 0.333, 0.666)
FROM df
```


## ST_LineInterpolatePoint

Returns a point interpolated along a line. First argument must be a LINESTRING. Second argument is a Double between 0 and 1 representing fraction of total linestring length the point has to be located.

Format: `ST_LineInterpolatePoint(geom: LineString, fraction: Double) `

Since: `v1.0.1`

Spark SQL example:
```SQL
SELECT ST_LineInterpolatePoint(df.geometry, 0.5)
FROM df
```
