package ru.test.distance.calculator.util;

import org.springframework.stereotype.Component;
import ru.test.distance.calculator.dto.CalculateResponseDto;
import ru.test.distance.calculator.dto.CityShortDto;
import ru.test.distance.calculator.dto.DistanceToMatrixDto;

import static ru.test.distance.calculator.dto.CalculationTypeEnum.DISTANCE_MATRIX;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DistanceMatrixCalculator {

    public List<CalculateResponseDto> calc(List<CityShortDto> cityShortDtoList, List<DistanceToMatrixDto> distanceToMatrixDtoList, List<CityShortDto> fromCity, List<CityShortDto> toCity) {

        Map<Long, CityNode> map = cityShortDtoList.stream().collect(Collectors.toMap(CityShortDto::getId, s -> new CityNode(s.getName())));

        AdjList aList = new AdjList();
        map.values().forEach(aList::addCity);
        distanceToMatrixDtoList.forEach(s -> aList.connectCities(map.get(s.getFromCity()), map.get(s.getToCity()), s.getDistance().intValue()));

        FindPath findPath = new FindPath();

        List<CalculateResponseDto> calculateResponseDtoList = new ArrayList<>();
        for (CityShortDto shortDto : fromCity) {
            for (CityShortDto cityShortDto : toCity) {
                Long i1 = findPath.calculateShortestPath(aList, map.get(shortDto.getId()), map.get(cityShortDto.getId()));
                CalculateResponseDto calculateResponseDto = new CalculateResponseDto();
                calculateResponseDto.setType(DISTANCE_MATRIX);
                calculateResponseDto.setDistance(i1);
                calculateResponseDto.setFromCity(shortDto.getName());
                calculateResponseDto.setToCity(cityShortDto.getName());
                calculateResponseDtoList.add(calculateResponseDto);
            }
        }
        return calculateResponseDtoList;
    }
}

class FindPath {

    Map<CityNode, Integer> distances;

    public Long calculateShortestPath(AdjList aList, CityNode from, CityNode to) {

        Set<CityNode> settledCities = new HashSet<>();
        Set<CityNode> unsettledCities = new HashSet<>();
        unsettledCities.add(from);

        distances = new HashMap<>();
        for (CityNode city : aList.getCities()) {
            int distance = city.equals(from) ? 0 : Integer.MAX_VALUE;
            distances.put(city, distance);
        }

        while (unsettledCities.size() != 0) {
            CityNode currentCity = getLowestDistanceCity(unsettledCities);
            unsettledCities.remove(currentCity);
            settledCities.add(currentCity);

            Map<CityNode, Integer> connectedCities = currentCity.getConnectedCities();

            for (CityNode city : connectedCities.keySet()) {
                if (distances.get(currentCity) + connectedCities.get(city) < distances.get(city)) {
                    distances.put(city, distances.get(currentCity) + connectedCities.get(city));
                    if (!settledCities.contains(city)) {
                        unsettledCities.add(city);
                    }
                }
            }
        }
        return distances.get(to).longValue();
    }

    private CityNode getLowestDistanceCity(Set<CityNode> unsettledCities) {

        return unsettledCities.stream()
                .min(Comparator.comparingInt(c -> distances.get(c)))
                .orElse(null);
    }
}

class CityNode {

    private static Long counter = 0L;
    private final String name;
    private final Long id = counter++;

    private final Map<CityNode, Integer> connectedCities;

    public CityNode(String name) {
        super();
        this.name = name;
        connectedCities = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void connect(CityNode connectTo, int distance) {
        if (connectTo.equals(this)) throw new IllegalArgumentException("Node can not connect to istself");
        connectedCities.put(connectTo, distance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(connectedCities.keySet().isEmpty() ? " not connected" : " conntected to: ");
        for (CityNode city : connectedCities.keySet()) {
            sb.append(city.getName()).append("-")
                    .append(connectedCities.get(city)).append("km ");
        }
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public Map<CityNode, Integer> getConnectedCities() {
        return connectedCities;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof CityNode)) return false;

        CityNode c = (CityNode) o;
        return c.getName().equalsIgnoreCase(name) && id == c.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}

class AdjList {

    private final Set<CityNode> citiesList;

    public AdjList() {
        citiesList = new HashSet<>();
    }

    public boolean addCity(CityNode city) {
        return citiesList.add(city);
    }

    public void connectCities(CityNode city1, CityNode city2, int distance) {
        city1.connect(city2, distance);
        city2.connect(city1, distance);
    }

    public Collection<CityNode> getCities() {
        return new ArrayList<>(citiesList);
    }
}