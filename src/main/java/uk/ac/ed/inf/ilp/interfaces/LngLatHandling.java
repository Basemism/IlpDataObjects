package uk.ac.ed.inf.ilp.interfaces;

import uk.ac.ed.inf.ilp.constant.CentralRegionVertexOrder;
import uk.ac.ed.inf.ilp.constant.SystemConstants;
import uk.ac.ed.inf.ilp.data.LngLat;
import uk.ac.ed.inf.ilp.data.NamedRegion;
import java.lang.Math;
/**
 * implement the needed computations for a LngLat
 */
public interface LngLatHandling {
    /**
     * get the distance between two positions
     * @param startPosition is where the start is
     * @param endPosition is where the end is
     * @return the euclidean distance between the positions
     */
    default double distanceTo(LngLat startPosition, LngLat endPosition) {
        double lngSum = Math.pow(startPosition.lng() - endPosition.lng(), 2);
        double latSum = Math.pow(startPosition.lat() - endPosition.lat(), 2);
        return Math.sqrt(lngSum+latSum);
    };

    /**
     * check if two positions are close (<= than SystemConstants.DRONE_IS_CLOSE_DISTANCE)
     * @param startPosition is the starting position
     * @param otherPosition is the position to check
     * @return if the positions are close
     */
    default boolean isCloseTo(LngLat startPosition, LngLat otherPosition) {
        return (distanceTo(startPosition, otherPosition) <= SystemConstants.DRONE_IS_CLOSE_DISTANCE);
    };

    /**
     * special handling shortcut for the central area. Here an implementation might add special improved processing as the central region is always rectangular
     * @param point to be checked
     * @param centralArea the central area
     * @return if the point is in the central area
     */
    default boolean isInCentralArea(LngLat point, NamedRegion centralArea) {
        if (centralArea == null){
            throw new IllegalArgumentException("the named region is null");
        }
        if (centralArea.name().equals(SystemConstants.CENTRAL_REGION_NAME) == false) {
            throw new IllegalArgumentException("the named region: " + centralArea.name() + " is not valid - must be: " + SystemConstants.CENTRAL_REGION_NAME);
        }

        return isInRegion(point, centralArea);
    }

    /**
     * check if the @position is in the @region (includes the border)
     * @param position to check
     * @param region as a closed polygon
     * @return if the position is inside the region (including the border)
     */
    default boolean isInRegion(LngLat position, NamedRegion region) {
        LngLat topLeft = region.vertices()[CentralRegionVertexOrder.TOP_LEFT];
        LngLat botLeft = region.vertices()[CentralRegionVertexOrder.BOTTOM_LEFT];
        LngLat botRight = region.vertices()[CentralRegionVertexOrder.BOTTOM_RIGHT];
        LngLat topRight = region.vertices()[CentralRegionVertexOrder.TOP_RIGHT];


    };

    /**
     * find the next position if an @angle is applied to a @startPosition
     * @param startPosition is where the start is
     * @param angle is the angle to use in degrees
     * @return the new position after the angle is used
     */
    LngLat nextPosition(LngLat startPosition, double angle);
}
