package com.pasantes_airtek.pasantestv.repository;

import com.pasantes_airtek.pasantestv.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
