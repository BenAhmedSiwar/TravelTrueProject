/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackOffice.Services;

import java.util.List;

/**
 *
 * @author iTech
 * @param <T>
 */
public interface IService<T> {

    public boolean insert(T o);
    public void delete(T o);
    public List<T> displayAll();
    public List<T> displayAllFX();
    public T displayById(int id);
    public boolean update(T os);

}
