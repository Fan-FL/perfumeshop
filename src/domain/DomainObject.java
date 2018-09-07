package domain;

public class DomainObject {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }else {
            if(this.getClass() == obj.getClass()){
                DomainObject u = (DomainObject) obj;
                if(this.getId() ==u.getId()){
                    return true;
                }else{
                    return false;
                }

            }else{
                return false;
            }
        }
    }
}
