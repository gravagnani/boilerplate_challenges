package bootey.utils;

import bootey.dto.*;
import bootey.enums.TileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Logic {

    public static SilverPoint getClosestSilverPoint(Point startPoint, List<SilverPoint> silverPoints, String direction) {
        SilverPoint closestSilverPoint = null;
        int closestDistance = Integer.MAX_VALUE;

        List<SilverPoint> pointsToRemove = new ArrayList<>(silverPoints);

        for (SilverPoint sp : silverPoints) {
            int distance;
            switch (direction) {
                case "up" -> {
                    // direzione verticale
                    if (sp.getY() > startPoint.getY() + 1) {
                        continue;
                    }
                    //   silverPoints.removeIf(silverPoint -> silverPoint.getY() > startPoint.getY() + 1);
                    //distance = Math.abs(startPoint.getY() - sp.getY());
                }
                case "left" -> {
                    // direzione orizzontale
                    if (sp.getX() > startPoint.getX() + 1) {
                        continue;
                    }
                    //silverPoints.removeIf(silverPoint -> silverPoint.getX() > startPoint.getX() + 1);
                    //distance = Math.abs(startPoint.getX() - sp.getX());
                }
                case "down" -> {

                    if (sp.getY() < startPoint.getY() - 1) {
                        continue;
                    }
                    // direzione verticale
                    //silverPoints.removeIf(silverPoint -> silverPoint.getY() < startPoint.getY() - 1);
                    //distance = Math.abs(startPoint.getY() - sp.getY());
                }
                case "right" -> {

                    if (sp.getX() < startPoint.getX() - 1) {
                        continue;
                    }
                    // direzione orizzontale
                    //silverPoints.removeIf(silverPoint -> silverPoint.getX() < startPoint.getX() - 1);
                    //distance = Math.abs(startPoint.getX() - sp.getX());
                }
                default -> {
                    throw new IllegalArgumentException("Direction must be either 'vertical' or 'horizontal'");
                }


            }
            distance = getDistance(startPoint, sp);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestSilverPoint = sp;
            }
        }

        return closestSilverPoint;
    }

    public static void findPathToClosestPoint(Point startPoint, Point closestPoint, List<Tile> tiles, ChallengeModel challenge, String direction) {
        int startX = startPoint.getX();
        int startY = startPoint.getY();
        int endX = closestPoint.getX();
        int endY = closestPoint.getY();

        Iterator<Tile> tileIterator = tiles.iterator();

        boolean changeType = true;


        while (!(startX == endX && startY == endY)) {
            if (!tileIterator.hasNext()) {
                throw new RuntimeException("No more tiles");
            }
            Tile tile = tileIterator.next();
            if (direction.equals("left") && tile.getType().getCanHorizontal() == 1) { // && startX > endX) {

                if (startX == endX) {

                    challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));

                    direction = startY > endY ? "up" : "down";

                    if (startY > endY) {
                        startY--;
                    } else {
                        startY++;
                    }
                    tileIterator.remove();
                } else if (startX > endX) {
                    if (changeType) {
                        challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                        changeType = false;
                    } else {
                        challenge.getSolutionTileList().add(new Tile(tile.getType(), tile.getCost(), tile.getAmount(), startX, startY));
                    }

                    startX--;
                    tileIterator.remove();
                }

            } else if (direction.equals("right") && tile.getType().getCanHorizontal() == 1) { // && startX < endX) {
                if (startX == endX) {
                    challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                    direction = startY > endY ? "up" : "down";

                    if (startY > endY) {
                        startY--;
                    } else {
                        startY++;
                    }
                    tileIterator.remove();
                } else if (startX < endX) {
                    if (changeType) {
                        challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                        changeType = false;
                    } else {
                        challenge.getSolutionTileList().add(new Tile(tile.getType(), tile.getCost(), tile.getAmount(), startX, startY));
                    }
                    startX++;
                    tileIterator.remove();
                }
            } else if (direction.equals("up") && tile.getType().getCanVertical() == 1) { // && startY > endY) {
                if (startY == endY) {
                    challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                    direction = startX > endX ? "left" : "right";

                    if (startX > endX) {
                        startX--;
                    } else {
                        startX++;
                    }
                    tileIterator.remove();
                } else if (startY > endY) {
                    if (changeType) {
                        challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                        changeType = false;
                    } else {
                        challenge.getSolutionTileList().add(new Tile(tile.getType(), tile.getCost(), tile.getAmount(), startX, startY));
                    }
                    startY--;
                    tileIterator.remove();
                }
            } else if (direction.equals("down") && tile.getType().getCanVertical() == 1) { // && startY < endY) {
                if (startY == endY) {
                    challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                    direction = startX > endX ? "left" : "right";

                    if (startX > endX) {
                        startX--;
                    } else {
                        startX++;
                    }
                    tileIterator.remove();
                } else if (startY < endY) {
                    if (changeType) {
                        challenge.getSolutionTileList().add(new Tile(TileType.INCROCIO_F, tile.getCost(), tile.getAmount(), startX, startY));
                        changeType = false;
                    } else {
                        challenge.getSolutionTileList().add(new Tile(tile.getType(), tile.getCost(), tile.getAmount(), startX, startY));
                    }
                    startY++;
                    tileIterator.remove();
                }
            }
        }
        // end while


    }

    private static int getDistance(Point sp, Point gp) {
        return Math.abs(sp.getX() - gp.getX()) + Math.abs(sp.getY() - gp.getY());
    }

    public Point getClosesGoldenPoint(ChallengeModel challenge, Point sp) {
        Point closesGoldenPoint = null;
        long lowestDistance = 10000000000000l;
        for (GoldenPoint gp : challenge.getGoldenPoints()) {
            int distance = getDistance(sp, gp);
            if (distance < lowestDistance) {
                closesGoldenPoint = gp;
            }

        }
        return closesGoldenPoint;
    }


}