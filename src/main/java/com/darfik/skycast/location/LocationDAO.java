package com.darfik.skycast.location;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.utils.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Log4j2
public class LocationDAO implements DAO<Location> {

    private static LocationDAO locationDAO;
    private LocationDAO() {}

    public static synchronized LocationDAO getInstance() {
        if (locationDAO == null) {
            locationDAO = new LocationDAO();
        }
        return locationDAO;
    }
    @Override
    public void save(Location location) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(location);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't save location, try again");
        }
    }

    @Override
    public Optional<Location> find(String name) {
        Location location = null;
        try (Session session = HibernateUtil.getSession()) {
            location = session.get(Location.class, name);
        } catch (HibernateException e) {
            log.error("Couldn't find the location");
        }
        return Optional.ofNullable(location);
    }

    @Override
    public List<Location> getAll() {
        List<Location> locations = null;
        try (Session session = HibernateUtil.getSession()) {
            locations = session.createQuery("FROM Location", Location.class).getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't get a list of location");
        }
        return locations;
    }

    @Override
    public void update(Location location, String[] params) {
        location.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(location);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't update the location");
        }

    }
}
