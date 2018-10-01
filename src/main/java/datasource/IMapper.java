package datasource;

import domain.DomainObject;

public interface IMapper {
    public int insert(DomainObject obj);
    public void update(DomainObject obj);
    public void delete(DomainObject obj);
    public DomainObject findById(int id);
}
