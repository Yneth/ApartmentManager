package ua.abond.lab4.dao.jdbc;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.annotation.Prop;
import ua.abond.lab4.core.annotation.Value;
import ua.abond.lab4.core.jdbc.JdbcTemplate;
import ua.abond.lab4.core.jdbc.KeyHolder;
import ua.abond.lab4.core.jdbc.PreparedStatementSetter;
import ua.abond.lab4.core.jdbc.RowMapper;
import ua.abond.lab4.core.jdbc.exception.DataAccessException;
import ua.abond.lab4.core.web.support.DefaultPage;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.dao.ApartmentDAO;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.web.dto.SearchApartmentDTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Prop("sql/apartment.sql.properties")
public class JdbcApartmentDAO extends JdbcDAO<Apartment>
        implements ApartmentDAO {
    @Value("sql.insert")
    private String insertSql;
    @Value("sql.update")
    private String updateSql;
    @Value("sql.getById")
    private String getByIdSql;
    @Value("sql.deleteById")
    private String deleteByIdSql;
    @Value("sql.list")
    private String listSql;
    @Value("sql.count")
    private String countSql;
    @Value("sql.filter")
    private String filterMostAppropriateSql;
    @Value("sql.filter.count")
    private String countMostAppropriateSql;
    @Value("sql.listFree")
    private String listFreeSql;
    @Value("sql.listFree.count")
    private String countFreeSql;

    @Inject
    public JdbcApartmentDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Apartment entity) {
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(
                    insertSql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, entity.getRoomCount());
            ps.setLong(2, entity.getType().getId());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setString(4, entity.getName());
            return ps;
        }, holder);
        entity.setId(holder.getKey().longValue());
    }

    @Override
    public Optional<Apartment> getById(Long id) {
        return jdbcTemplate.querySingle(getByIdSql,
                ps -> ps.setLong(1, id),
                new ApartmentMapper()
        );
    }

    @Override
    public void update(Apartment entity) {
        jdbcTemplate.execute(updateSql,
                ps -> {
                    ps.setInt(1, entity.getRoomCount());
                    ps.setLong(2, entity.getType().getId());
                    ps.setBigDecimal(3, entity.getPrice());
                    ps.setString(4, entity.getName());
                    ps.setLong(5, entity.getId());
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.execute(deleteByIdSql,
                ps -> ps.setLong(1, id)
        );
    }

    @Override
    public Page<Apartment> list(Pageable pageable) {
        long count = count();
        List<Apartment> query = jdbcTemplate.query(
                String.format(listSql, pageable.getPageSize(), pageable.getOffset()),
                new ApartmentMapper()
        );
        return new DefaultPage<>(query, count, pageable);
    }

    @Override
    public Page<Apartment> list(Pageable pageable, Request filter) {
        PreparedStatementSetter pss = ps -> {
            ps.setInt(1, filter.getLookup().getRoomCount());
            ps.setLong(2, filter.getLookup().getType().getId());
            ps.setDate(3, Date.valueOf(filter.getTo()));
            ps.setDate(4, Date.valueOf(filter.getFrom()));
        };
        long count = jdbcTemplate.querySingle(countMostAppropriateSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));

        List<Apartment> query = jdbcTemplate.query(
                String.format(filterMostAppropriateSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new ApartmentMapper()
        );
        return new DefaultPage<>(query, count, pageable);
    }

    @Override
    public Page<Apartment> list(Pageable pageable, SearchApartmentDTO filter) {
        PreparedStatementSetter pss = ps -> {
            ps.setInt(1, filter.getRoomCount());
            ps.setString(2, filter.getApartmentTypeIds().stream().
                    map(Object::toString).
                    collect(Collectors.joining(","))
            );
            ps.setDate(3, Date.valueOf(filter.getCheckOut()));
            ps.setDate(4, Date.valueOf(filter.getCheckIn()));
        };
        long count = jdbcTemplate.querySingle(countMostAppropriateSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));

        List<Apartment> query = jdbcTemplate.query(
                String.format(filterMostAppropriateSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new ApartmentMapper()
        );
        return new DefaultPage<>(query, count, pageable);
    }

    @Override
    public Page<Apartment> listFree(Pageable pageable, LocalDate from, LocalDate to) {
        PreparedStatementSetter pss = rs -> {
            rs.setDate(1, Date.valueOf(to));
            rs.setDate(2, Date.valueOf(from));
        };
        long count = jdbcTemplate.querySingle(countFreeSql, pss, rs -> rs.getLong(1)).
                orElseThrow(() -> new DataAccessException("Count cannot be null."));

        List<Apartment> query = jdbcTemplate.query(
                String.format(listFreeSql, pageable.getPageSize(), pageable.getOffset()),
                pss,
                new ApartmentMapper()
        );
        return new DefaultPage<>(query, count, pageable);
    }

    @Override
    public long count() {
        return jdbcTemplate.querySingle(countSql, rs -> rs.getLong(1)).orElse(0L);
    }

    private static class ApartmentMapper implements RowMapper<Apartment> {

        @Override
        public Apartment mapRow(ResultSet rs) throws SQLException {
            Apartment apartment = new Apartment();

            apartment.setId(rs.getLong(1));
            apartment.setRoomCount(rs.getInt(2));
            ApartmentType type = new ApartmentType();
            type.setId(rs.getLong(3));
            type.setName(rs.getString(4));
            apartment.setPrice(rs.getBigDecimal(5));
            apartment.setType(type);
            apartment.setName(rs.getString(6));
            return apartment;
        }
    }
}
