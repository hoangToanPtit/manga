package vn.ptit.manga.payload.response;

import lombok.Data;
import vn.ptit.manga.dto.MangaDTO;
import java.util.List;


@Data
public class MangaResponse {
    private int page;
    private int limit;
    private long total;
    private List<MangaDTO> data;
}
