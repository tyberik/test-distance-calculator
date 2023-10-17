package ru.test.distance.calculator.util;
import org.springframework.stereotype.Component;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.entity.DistanceEntity;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DistanceMatrixCalculator {

    public void calc(List<CityEntity> cityEntity, List<DistanceEntity> distanceEntity, Long fromCity, Long toCity) {

        Map<Long, CityNode> map = cityEntity.stream().collect(Collectors.toMap(CityEntity::getId, s -> new CityNode(s.getName())));
        AdjList aList = new AdjList();

        map.values().forEach(aList::addCity);


        distanceEntity.forEach(s -> aList.connectCities(map.get(s.getFromCity()), map.get(s.getToCity()), s.getDistance().intValue()));

        aList.show();

        FindPath findPath = new FindPath();
        System.out.println(findPath.calculateShortestPath(aList, map.get(fromCity), map.get(toCity))); //prints 140 as expected

    }
}

class FindPath{

    //map to hold distances of all node from origin. at the end this map should contain
    //the shortest distance between origin (from) to all other nodes
    Map<CityNode, Integer> distances;

    //using Dijkstra algorithm
    public int calculateShortestPath(AdjList aList, CityNode from, CityNode to) {

        //a container to hold which cities the algorithm has visited
        Set<CityNode> settledCities = new HashSet<>();
        //a container to hold which cities the algorithm has to visit
        Set<CityNode> unsettledCities = new HashSet<>();
        unsettledCities.add(from);

        //map to hold distances of all node from origin. at the end this map should contain
        //the shortest distance between origin (from) to all other nodes
        distances = new HashMap<>();
        //initialize map with values: 0 distance to origin, infinite distance to all others
        //infinite means no connection between nodes
        for(CityNode city :aList.getCities()){
            int distance = city.equals(from) ? 0 : Integer.MAX_VALUE;
            distances.put(city, distance);
        }

        while (unsettledCities.size() != 0) {
            //get the unvisited city with the lowest distance
            CityNode currentCity = getLowestDistanceCity(unsettledCities);
            //remove from unvisited, add to visited
            unsettledCities.remove(currentCity);
            settledCities.add(currentCity);

            Map<CityNode, Integer> connectedCities = currentCity.getConnectedCities();

            for( CityNode city : connectedCities.keySet()){
                //check if new distance is shorted than the previously found distance
                if(distances.get(currentCity) + connectedCities.get(city) < distances.get(city)){
                    //if so, keep the shortest distance found
                    distances.put(city, distances.get(currentCity) + connectedCities.get(city));
                    //if city has not been visited yet, add it to unsettledCities
                    if(! settledCities.contains(city)) {
                        unsettledCities.add(city);
                    }
                }
            }
        }

        return distances.get(to);
    }

    private CityNode getLowestDistanceCity(Set <CityNode> unsettledCities) {

        return unsettledCities.stream()
                .min((c1,c2)-> Integer.compare(distances.get(c1), distances.get(c2)))
                .orElse(null);
    }
}

class CityNode {

    private static int counter =0;
    private final String name;
    //assign unique id to each node. safer than to rely on unique name
    private final int id = counter ++;

    //map to hold all connected cities and distance to each
    private final Map<CityNode, Integer> connectedCities;

    public CityNode(String name) {
        super();
        this.name = name;
        connectedCities = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    //not null safe. distance must not be negative
    public void connect(CityNode connectTo, int distance) {
        if(connectTo.equals(this)) throw new IllegalArgumentException("Node can not connect to istself");
        connectedCities.put(connectTo, distance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(connectedCities.keySet().isEmpty() ? " not connected" : " conntected to: " );
        for ( CityNode city : connectedCities.keySet()) {
            sb.append(city.getName()).append("-")
                    .append(connectedCities.get(city)).append("km ");
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public Map<CityNode, Integer> getConnectedCities(){
        return connectedCities;
    }

    @Override
    public boolean equals(Object o) {

        if(o == null ||!(o instanceof CityNode)) return false;

        CityNode c = (CityNode) o;
        return c.getName().equalsIgnoreCase(name) && id == c.getId();
    }

    @Override
    public int hashCode() {
        int hash = 31 * 7 + id;
        return name == null ? hash : name.hashCode();
    }
}

class AdjList {

    //use set which prevents duplicate entries
    private final Set<CityNode> citiesList;

    public AdjList() {
        citiesList = new HashSet<>();
    }

    //adds city if is not already present. returns true if city was added
    public boolean addCity(CityNode city) {

        return citiesList.add(city);
    }

    //not null safe
    public void connectCities(CityNode city1, CityNode city2, int distance) {
        //assuming undirected graph
        city1.connect(city2, distance);
        city2.connect(city1, distance);
    }

    public CityNode getCityByName(String name) {
        for (CityNode city : citiesList) {
            if (city.getName().equalsIgnoreCase(name))
                return city;
        }
        return null;
    }

    public void show() {
        for (CityNode city : citiesList) {
            System.out.println(city);
        }
    }

    //get a copy of cities list
    public Collection<CityNode> getCities(){

        return new ArrayList<>(citiesList);
    }
}

