package nextstep.subway.line.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import nextstep.subway.common.domain.model.BaseEntity;
import nextstep.subway.station.domain.model.Station;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String color;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<Section> sections;

    protected Line() {
        this.sections = new ArrayList<>();
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
        this.sections = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void edit(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public boolean matchSectionsSize(int size) {
        return this.sections.size() == size;
    }

    public boolean notMatchName(String name) {
        return !this.name.equals(name);
    }

    public Section createSection(Station upStation, Station downStation, Distance distance) {
        Section section = Section.builder()
            .upStation(upStation)
            .downStation(downStation)
            .distance(distance)
            .build();
        this.sections.add(section);
        return section;
    }
}
