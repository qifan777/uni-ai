package io.qifan.server.infrastructure.jimmer;

import java.time.LocalDateTime;
import org.babyfish.jimmer.sql.MappedSuperclass;

@MappedSuperclass
public interface BaseDateTime {

  LocalDateTime createdTime();

  LocalDateTime editedTime();
}
