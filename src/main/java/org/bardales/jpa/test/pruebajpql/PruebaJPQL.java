package org.bardales.jpa.test.pruebajpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.domain.Usuario;

@Log4j2
public class PruebaJPQL {

    private EntityManagerFactory entityManagerFactory;

    public PruebaJPQL() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    private static void mostrar(List<?> list) {
        list.forEach(l -> LOG.info("{} : {}", l.getClass().getSimpleName(), l));
    }

    public static void main(String... args) {
        PruebaJPQL p = new PruebaJPQL();

        String jpql = "";
        List<Persona> personas = null;
        Persona persona = null;
        Iterator iter = null;
        Object[] tupla = null;
        List<String> nombres = null;
        List<Usuario> usuarios = null;
        TypedQuery<Persona> typedQueryPersona = null;

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();

            //1. Consulta de todos los objetos de tipo Persona
            LOG.info("1. Consulta de todas las Personas");
            jpql = "select p from Persona p ";
            personas = entityManager.createQuery(jpql, Persona.class).getResultList();
            mostrar(personas);

            //2. Consulta de la Persona con id = 917
            LOG.info("2. Consulta de la Persona con id = 917");
            jpql = "select p from Persona p where p.idPersona = 917";
            persona = entityManager.createQuery(jpql, Persona.class).getSingleResult();
            LOG.info("persona : {}", persona);

            //3. Consulta de la Persona por nombre
            LOG.info("3. Consulta de la Persona con nombre = 'lucas'");
            jpql = "select p from Persona p where p.nombre = 'lucas'";
            personas = entityManager.createQuery(jpql, Persona.class).getResultList();
            mostrar(personas);

            //4. Consulta de datos individuales, se crea un arreglo(tupla) de tipo object de 3 columnas
            LOG.info(
                    "4. Consulta de datos individuales, se crea un arreglo(tupla) de tipo object de 3 columnas");
            jpql = "select p.nombre as Nombre, p.apellido as Apellido, p.email as Email from Persona p";
            iter = entityManager.createQuery(jpql).getResultList().iterator();

            while (iter.hasNext()) {
                tupla = (Object[]) iter.next();
                String nombre = (String) tupla[0];
                String apellido = (String) tupla[1];
                String email = (String) tupla[2];
                LOG.info("nombre: {} , apellido: {} , email: {}", nombre, apellido, email);
            }

            //5. Obtiene el objeto persona y el id, se crea un arreglo de tipo Object con 2 columnas
            LOG.info(
                    "5. Obtiene el objeto persona y el id, se crea un arreglo de tipo Object con 2 columnas");
            jpql = "select p, p.idPersona from Persona p";
            iter = entityManager.createQuery(jpql).getResultList().iterator();

            while (iter.hasNext()) {
                tupla = (Object[]) iter.next();
                persona = (Persona) tupla[0];
                int idPersona = (Integer) tupla[1];
                LOG.info("Objeto Persona: {}", persona);
                LOG.info("id persona: {}", idPersona);
            }

            //6. Regresa el valor minimo y maximo del idPersona (scaler result)
            LOG.info("7. Regresa el valor minimo y maximo del idPersona (scaler result)");
            jpql = "select min(p.idPersona) as MinId, max(p.idPersona) as MaxId, count(p.idPersona) as Contador from Persona p";
            iter = entityManager.createQuery(jpql).getResultList().iterator();

            while (iter.hasNext()) {
                tupla = (Object[]) iter.next();
                int minId = (Integer) tupla[0];
                int maxId = (Integer) tupla[1];
                long contador = (Long) tupla[2];
                LOG.info("minId : {}", minId);
                LOG.info("maxId : {}", maxId);
                LOG.info("contador : {}", contador);
            }

            //7. Cuenta los nombres de las personas que son distintos
            LOG.info("8. Cuenta los nombres de las personas que son distintos");
            jpql = "select count(distinct p.nombre) as Contador from Persona p";
            Long contador = entityManager.createQuery(jpql, Long.class).getSingleResult();
            LOG.info("contador : {}", contador);

            //8. Concatena y convierte a mayúsculas el nombre y el apellido
            LOG.info("9. Concatena y convierte a mayúsculas el nombre y el apellido");
            jpql = "select upper(concat(p.nombre, ' ', p.apellido)) as NombreCompleto from Persona p";
            nombres = entityManager.createQuery(jpql, String.class).getResultList();
            nombres.forEach(n -> LOG.info("Nombre: {}", n));

            //9. Obtiene el objeto persona con el id igual al parametro proporcionado
            LOG.info("10. Obtiene el objeto persona con el id igual al parametro proporcionado");
            int idPersona = 917;
            jpql = "select p from Persona p where p.idPersona = :idPersona";
            typedQueryPersona = entityManager.createQuery(jpql, Persona.class);
            typedQueryPersona.setParameter("idPersona", idPersona);
            persona = typedQueryPersona.getSingleResult();
            LOG.info("persona : {}", persona);

            //10. Obtiene las personas que contengan una letra a en el nombre, sin importar si es mayusculas o minusculas
            LOG.info(
                    "11. Obtiene las personas que contengan una letra l en el nombre, sin importar si es mayusculas o minusculas>");
            String paramLike = "%l%";
            jpql = "select p from Persona p where upper(p.nombre) like upper(:paramLike)";
            typedQueryPersona = entityManager.createQuery(jpql, Persona.class);
            typedQueryPersona.setParameter("paramLike", paramLike);
            personas = typedQueryPersona.getResultList();
            mostrar(personas);

            //11. Uso de between
            LOG.info("12. Uso de between");
            jpql = "select p from Persona p where p.idPersona between 10 and 100";
            personas = entityManager.createQuery(jpql, Persona.class).getResultList();
            mostrar(personas);

            //12. Uso del ordenamiento
            LOG.info("13. Uso del ordenamiento");
            jpql = "select p from Persona p where p.idPersona > 1 order by p.nombre desc, p.apellido desc";
            personas = entityManager.createQuery(jpql, Persona.class).getResultList();
            mostrar(personas);

            //13. Uso de subquery
            LOG.info("14. Uso de subquery");
            jpql = "select p from Persona p where p.idPersona in (select min(p2.idPersona) from Persona p2)";
            personas = entityManager.createQuery(jpql, Persona.class).getResultList();
            mostrar(personas);

            //14. Uso de inner join con lazy loading
            LOG.info("15. Uso de inner join con lazy loading");
            jpql = "select u from Usuario u join u.persona p";
            usuarios = entityManager.createQuery(jpql, Usuario.class).getResultList();
            mostrar(usuarios);

            //15. Uso de left join con eager loading
            LOG.info("16. Uso de left join con eager loading");
            jpql = "select u from Usuario u left join fetch u.persona p";
            usuarios = entityManager.createQuery(jpql, Usuario.class).getResultList();
            mostrar(usuarios);

            //16. Uso de inner join, group by y having con eager loading
            LOG.info("17. Uso de inner join, group by y having con eager loading");
            jpql = "select p.nombre, count(u.idUsuario) as cantidadUsuarios from Usuario u join u.persona p"
                    + " group by p.nombre having count(u.idUsuario) > 1";
            iter = entityManager.createQuery(jpql).getResultList().iterator();

            while (iter.hasNext()) {
                tupla = (Object[]) iter.next();
                String nombre = (String) tupla[0];
                Long cantidadUsuarios = (Long) tupla[1];
                LOG.info("nombre : {}", nombre);
                LOG.info("cantidadUSuarios : {}", cantidadUsuarios);
            }

        } catch (Exception e) {
            if (Objects.nonNull(tx)) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(entityManager)) {
                entityManager.close();
            }
        }

    }

}

