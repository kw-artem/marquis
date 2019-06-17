package cf.creativeflow.marquis.blog.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_dt", updatable = false, nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "changed_dt")
    private Date lastChangeDate;

    //private String description;

    private String tags;
}
