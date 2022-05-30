package hellojpa;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    private String createBy;
    private String lastModifiedBy;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
