package com.rednorte.gestionsoporte.repository;

import com.rednorte.gestionsoporte.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}