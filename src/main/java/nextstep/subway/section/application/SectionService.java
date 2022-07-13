package nextstep.subway.section.application;

import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.section.application.dto.SectionRequest;
import nextstep.subway.section.application.dto.SectionResponse;
import nextstep.subway.section.domain.Section;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SectionService {

    private final LineService lineService;

    public SectionService(final LineService lineService) {
        this.lineService = lineService;
    }

    @Transactional
    public SectionResponse addSection(final long lineId, final SectionRequest sectionRequest) {
        final Line line = lineService.findLineById(lineId);
        final Section section = sectionRequest.toSection();

        if (!line.isConnectable(section)) {
            throw new IllegalArgumentException("신규상행역이 기존의 하행역이 아닙니다.");
        }

        if (line.hasCircularSection(section)) {
            throw new IllegalArgumentException("신규하행역이 이미 등록되어 있습니다.");
        }

        line.addSection(section);
        return createSectionResponse(section);
    }

    private SectionResponse createSectionResponse(final Section section) {
        return SectionResponse.builder()
                .id(section.getId())
                .upStationId(section.getUpStationId())
                .downStationId(section.getDownStationId())
                .distance(section.getDistance())
                .build();
    }

    @Transactional
    public void deleteSection(final Long lineId, final Long stationId) {
        final Line line = lineService.findLineById(lineId);
        line.removeStation(stationId);
    }

}
