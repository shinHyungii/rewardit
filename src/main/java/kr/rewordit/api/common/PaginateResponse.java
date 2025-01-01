package kr.rewordit.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginateResponse<T> {

    private Integer page;

    private Integer perPage;

    private List<T> items;


    public PaginateResponse(Integer page, Integer perPage, List<T> items) {
        this.page = page;
        this.perPage = perPage;
        this.items = items;
    }


    public PaginateResponse(PagingRequest request, List<T> items) {
        this.page = request.getPage();
        this.perPage = request.getPerPage();
        this.items = items;
    }
}
