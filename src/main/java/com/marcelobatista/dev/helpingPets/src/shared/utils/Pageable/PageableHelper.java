package com.marcelobatista.dev.helpingPets.src.shared.utils.Pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableHelper {
  public static Pageable createPageable(Integer page, Integer size) {
    if (size == null) {
      return Pageable.unpaged();
    }

    return PageRequest.of(page != null ? page : 0, size);
  }
}
