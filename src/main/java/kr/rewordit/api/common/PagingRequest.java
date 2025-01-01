package kr.rewordit.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class PagingRequest {

    private Integer page;

    private Integer perPage;

    public Pageable page() {
        init();

        Pageable pageable = Pageable
            .ofSize(this.perPage)
            .withPage(this.page - 1);

        pageable.getSort()
            .descending()
            .getOrderFor("id");
        return pageable;
    }

    private void init() {
        if (this.page == null) {
            this.page = 1;
        }

        if (this.perPage == null) {
            this.perPage = 10;
        }
    }
}
