package org.bardales.jpa.test.apicriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.domain.Usuario;

@Log4j2
public class PruebaApiCriteria {

    private EntityManagerFactory entityManagerFactory;

    public PruebaApiCriteria() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void mostrar(List<?> lista) {
        lista.stream().forEach(l -> LOG.info("{} : {}", l.getClass().getSimpleName(), l));
    }

    public static void main(String... args) {
        PruebaApiCriteria p = new PruebaApiCriteria();

        EntityTransaction tx = null;
        EntityManager entityManager = null;
        CriteriaBuilder cb = null;
        CriteriaQuery<Long> criteriaQueryLong = null;
        CriteriaQuery<String> criteriaQueryString = null;
        CriteriaQuery<Persona> criteriaQueryPersona = null;
        CriteriaQuery<Usuario> criteriaQueryUsuario = null;
        CriteriaQuery<Tuple> criteriaQueryTuple = null;
        CriteriaUpdate<Usuario> criteriaUpdateUsuario = null;
        CriteriaDelete<Usuario> criteriaDeleteUsuario = null;
        Root<Usuario> fromUsuario = null;
        Root<Persona> fromPersona = null;
        TypedQuery<Long> typedQueryLong = null;
        TypedQuery<String> typedQueryString = null;
        TypedQuery<Persona> typedQueryPersona = null;
        TypedQuery<Usuario> typedQueryUsuario = null;
        TypedQuery<Tuple> typedQueryTuple = null;
        Tuple tuple = null;
        List<Long> longList = null;
        List<String> stringList = null;
        List<Persona> personaList = null;
        List<Usuario> usuarioList = null;
        List<Tuple> tupleList = null;
        Persona persona = null;
        SetJoin<Object, Object> setjoinFromUsuario = null;
        Join<Object, Object> joinFromPersona = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();

            /*
             El CriteriaBuilder te permite crea querys, predicados y expresiones.

             Con CriteriaBuilder puedes crear varios tipo de Query
             -createQuery(class) : una query para select que retona un tipo de Clase en especifico
             -createTupleQuery : en caso necesites retornar un tupla
             -createCriteriaUpdate<class> : una query para update sobre un tipo de Clase en especifico
             -createCriteriaDelete<class> : una query para delete sobre un tipo de Clase en especifico
             */

            //1. Consulta de todos los objetos de tipo Persona
            LOG.info("1. Consulta de todos los objetos de tipo Persona");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryPersona = cb.createQuery(Persona.class);

            //Paso3. Creamos el objeto raiz del query
            fromPersona = criteriaQueryPersona.from(Persona.class);

            //Paso4. Se construye la consulta
            criteriaQueryPersona.select(fromPersona);

            //Paso5. Creamos el query typesafe
            typedQueryPersona = entityManager.createQuery(criteriaQueryPersona);

            //Pas6. Se ejecuta la consulta
            personaList = typedQueryPersona.getResultList();
            mostrar(personaList);

            //2. Consulta de la Persona con id = 917
            LOG.info("2. Consulta de la Persona con id = 91");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryPersona = cb.createQuery(Persona.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryPersona.from(Persona.class);

            //Paso4. Creamos una instancia de Predicate que contiene la condicional
            Predicate equalIdPersona = cb.equal(fromPersona.get("idPersona"), 917);

            //Paso5. Se construye la consulta
            criteriaQueryPersona.select(fromPersona)
                    .where(equalIdPersona);

            //Paso6. Creamos el query typesafe
            typedQueryPersona = entityManager.createQuery(criteriaQueryPersona);

            //Paso7. Se ejecuta la consulta
            persona = typedQueryPersona.getSingleResult();
            LOG.info("persona : {}", persona);

            //3. Consulta de la Personas que contengan los idPersona especificados
            LOG.info("3. Consulta de la Personas que contengan los idPersona especificados");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryPersona = cb.createQuery(Persona.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryPersona.from(Persona.class);

            //Paso4. Creamos una instancia de Predicate que contiene la condicional
            Predicate inIdPersona = fromPersona.get("idPersona").in(507, 917);

            //Paso5. Se construye una consulta
            criteriaQueryPersona.select(fromPersona).where(inIdPersona);

            //Paso6. Creamos el query typesafe
            typedQueryPersona = entityManager.createQuery(criteriaQueryPersona);

            //Paso7. Se ejecuta la consulta
            personaList = typedQueryPersona.getResultList();
            mostrar(personaList);

            //4. Consulta de los datos individuales, se crea un arreglo(tupla) de tipo Object de 3 columnas
            LOG.info(
                    "4. Consulta de los datos individuales, se crea un arreglo(tupla) de tipo Object de 3 columnas");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryTuple = cb.createTupleQuery();

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryTuple.from(Persona.class);

            //Paso4, Se construye una consulta
            criteriaQueryTuple.multiselect(fromPersona.get("nombre").alias("nom"),
                    fromPersona.get("apellido").alias("ape"),
                    fromPersona.get("email").alias("email"));

            //Paso5. Creamos el query typesafe
            typedQueryTuple = entityManager.createQuery(criteriaQueryTuple);

            //Paso6. Se ejecuta la consulta
            tupleList = typedQueryTuple.getResultList();

            tupleList.forEach(t -> {
                String nombre = t.get("nom", String.class);
                String apellido = t.get("ape", String.class);
                String email = t.get("email", String.class);
                LOG.info("nombre : {} , apellido : {} , email : {}", nombre, apellido, email);
            });

            //5. Obtiene le objeto persona y el id, se crea un arreglo de tipo Object con 2 columnas
            LOG.info(
                    "5. Obtiene le objeto persona y el id, se crea un arreglo de tipo Object con 2 columnas");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryTuple = cb.createQuery(Tuple.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryTuple.from(Persona.class);

            //Paso4, Se construye una consulta
            criteriaQueryTuple.multiselect(fromPersona.alias("persona"),
                    fromPersona.get("idPersona").alias("id"));

            //Paso5. Creamos el query typesafe
            typedQueryTuple = entityManager.createQuery(criteriaQueryTuple);

            //Paso6. Se ejecuta la consulta
            tupleList = typedQueryTuple.getResultList();

            tupleList.forEach(t -> {
                Persona person = t.get("persona", Persona.class);
                int id = t.get("id", Integer.class);
                LOG.info("persona : {} , id : {}", person, id);
            });

            //6. Regresa el valor minimo y maximo del idPersona (scaler result)
            LOG.info("6. Regresa el valor minimo y maximo del idPersona (scaler result)");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryTuple = cb.createTupleQuery();

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryTuple.from(Persona.class);

            //Paso4, Se construye una consulta
            criteriaQueryTuple
                    .multiselect(cb.min(fromPersona.get("idPersona")).alias("idmin"), cb.max(
                            fromPersona.get("idPersona")).alias("idmax"));

            //Paso5. Creamos el query typesafe
            typedQueryTuple = entityManager.createQuery(criteriaQueryTuple);

            //Paso6. Se ejecuta la consulta
            tuple = typedQueryTuple.getSingleResult();

            int idMin = tuple.get("idmin", Integer.class);
            int idMax = tuple.get("idmax", Integer.class);
            LOG.info("idmin : {} , idmax : {}", idMin, idMax);

            //7. Cuenta los nombres de las personas que son distintos
            LOG.info("7. Cuenta los nombres de las personas que son distintos");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryLong = cb.createQuery(Long.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryLong.from(Persona.class);

            //Paso4, Se construye una consulta
            criteriaQueryLong.select(cb.countDistinct(fromPersona.get("nombre")));

            //Paso5. Creamos el query typesafe
            typedQueryLong = entityManager.createQuery(criteriaQueryLong);

            //Paso6. Se ejecuta la consulta
            Long cantNombres = typedQueryLong.getSingleResult();
            LOG.info("cantNombres : {}", cantNombres);

            //8. Concatena y convierte a mayusculas el nombre y el apellido
            LOG.info("8. Concatena y convierte a mayusculas el nombre y el apellido");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryString = cb.createQuery(String.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryString.from(Persona.class);

            //Paso4. Se instancian las expresiones
            Expression<String> expressionNombreMayus = cb.upper(fromPersona.get("nombre"));
            Expression<String> expressionApellidoMayus = cb.upper(fromPersona.get("apellido"));

            Expression<String> expressionConcatNombreYApellido = cb
                    .concat(expressionNombreMayus, cb.concat(" ", expressionApellidoMayus));

            //Paso5. Se construye una consulta
            criteriaQueryString.select(expressionConcatNombreYApellido);

            //Paso6. Creamos el query typesafe
            typedQueryString = entityManager.createQuery(criteriaQueryString);

            //Paso7. Se ejecuta la consulta
            stringList = typedQueryString.getResultList();
            mostrar(stringList);

            //9. Obtiene las personas que contengan el id ingresado en un rango especifico, ademas el nombre
            //debe contener la letra l sin importar mayusculas y minusculas
            LOG.info(
                    "9. Obtiene las personas que contengan el id ingresado en un rango especifico, ademas el nombre");

            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryPersona = cb.createQuery(Persona.class);

            //Paso3. Creamos el objeto raiz del Query
            fromPersona = criteriaQueryPersona.from(Persona.class);

            //Paso4 Estableciendo parametros
            int id = 7;

            Map<ParameterExpression, Object> paramMap = new HashMap<>();

            ParameterExpression<Integer> parameterIDPersona = cb
                    .parameter(Integer.class, "idPersona");
            paramMap.put(parameterIDPersona, id);

            ParameterExpression<Integer> parameterBetweenMinId = cb
                    .parameter(Integer.class, "betweenMinId");
            paramMap.put(parameterBetweenMinId, 1);

            ParameterExpression<Integer> parameterBetweenMaxId = cb
                    .parameter(Integer.class, "betweenMaxId");
            paramMap.put(parameterBetweenMaxId, 1000);

            ParameterExpression<String> parameterLikeNombre = cb
                    .parameter(String.class, "likeNombre");
            paramMap.put(parameterLikeNombre, "%L%");

            //Paso5 Se instancian las expresiones
            Expression<String> expressionNombreUpper = cb.upper(fromPersona.get("nombre"));

            //Paso6 Estableciendo los predicados
            List<Predicate> predicateList = new ArrayList<>();

            Predicate predicateBetweenIdPersona = cb
                    .between(fromPersona.get("idPersona"), parameterBetweenMinId,
                            parameterBetweenMaxId);
            predicateList.add(predicateBetweenIdPersona);

            Predicate predicateLikeNombre = cb.like(expressionNombreUpper, parameterLikeNombre);
            predicateList.add(predicateLikeNombre);

            Predicate predicateEqualIdPersona = cb
                    .equal(fromPersona.get("idPersona"), parameterIDPersona);
            predicateList.add(predicateEqualIdPersona);

            //Paso7 Se construye una consulta
            criteriaQueryPersona.where(cb.and(predicateList.toArray(Predicate[]::new)));

            //Paso8 Creamos el query typesafe
            typedQueryPersona = entityManager.createQuery(criteriaQueryPersona);

            for (Map.Entry<ParameterExpression, Object> entry : paramMap.entrySet()) {
                if (entry.getKey().equals("idPersona")
                        || entry.getKey().equals("betweenMinId")
                        || entry.getKey().equals("betweenMaxId")) {
                    typedQueryPersona.setParameter(entry.getKey(), (Integer) entry.getValue());
                }

                if (entry.getKey().equals("likeNombre")) {
                    typedQueryPersona.setParameter(entry.getKey(), (String) entry.getValue());
                }
            }

            //Paso9 Se ejecuta la consulta
            personaList = typedQueryPersona.getResultList();
            mostrar(personaList);

            //12. Uso de subquery para listar las personas que tienen mas usuarios
            LOG.info("12. Uso de subquery para listart las personas que tienen mas usuarios");

            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryLong = cb.createQuery(Long.class);

            //Paso3. Obtenemos la cantidad de usuarios maximo que tiene una persona
            fromUsuario = criteriaQueryLong.from(Usuario.class);

            //Paso4. Se construye la consulta
            criteriaQueryLong.select(cb.count(fromUsuario.get("idUsuario")))
                    .groupBy(fromUsuario.get("persona").get("idPersona"));

            //Paso5 Creamos el query typesafe
            typedQueryLong = entityManager.createQuery(criteriaQueryLong);

            //Paso6. Ejecutamos la consulta
            longList = typedQueryLong.getResultList();

            long cantUsuariosMax = longList.stream().max(Long::compare).orElse(-1L);
            LOG.info("cantUsuariosMax : {}", cantUsuariosMax);

            //Paso7. Se crea la instancia de CriteriaQuery
            criteriaQueryPersona = cb.createQuery(Persona.class);

            //Paso8. Obtenemos las personas que tiene la cantidad maxima de usuarios
            fromPersona = criteriaQueryPersona.from(Persona.class);

            //Paso9. Se construye la consulta
            Subquery<Integer> subqueryInteger = criteriaQueryPersona.subquery(Integer.class);
            fromUsuario = subqueryInteger.from(Usuario.class);

            subqueryInteger.select(fromUsuario.get("persona").get("idPersona"))
                    .groupBy(fromUsuario.get("persona").get("idPersona"))
                    .having(cb.equal(cb.count(fromUsuario.get("idUsuario")), cantUsuariosMax));

            criteriaQueryPersona.where(fromPersona.get("idPersona").in(subqueryInteger));

            //Paso10. Creamos el query typesafe
            typedQueryPersona = entityManager.createQuery(criteriaQueryPersona);

            //Paso11. Ejecutamos la consulta
            personaList = typedQueryPersona.getResultList();
            mostrar(personaList);

            //13. Uso de inner join con lazy loading
            LOG.info("13. Uso de inner join con lazy loading");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryUsuario = cb.createQuery(Usuario.class);

            //Paso3. Creamos el objeto raiz del Query
            fromUsuario = criteriaQueryUsuario.from(Usuario.class);

            //Paso4. Se genera el inner join
            joinFromPersona = fromUsuario.join("persona", JoinType.INNER);

            //Paso5. Creamos el query typesafe
            typedQueryUsuario = entityManager.createQuery(criteriaQueryUsuario);

            //Paso6. Se ejecuta la consulta
            usuarioList = typedQueryUsuario.getResultList();
            mostrar(usuarioList);

            //14. Uso de left join con eager loading
            LOG.info("14. Uso de left join con eager loading");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaQuery
            criteriaQueryUsuario = cb.createQuery(Usuario.class);

            //Paso3. Creamos el objeto raiz del Query
            fromUsuario = criteriaQueryUsuario.from(Usuario.class);

            //Paso4. Se genera el left join
            joinFromPersona = (Join<Object, Object>) fromUsuario.fetch("persona", JoinType.LEFT);

            //Paso5. Creamos el query typesafe
            typedQueryUsuario = entityManager.createQuery(criteriaQueryUsuario);

            //Paso6. Se ejecuta la consulta
            usuarioList = typedQueryUsuario.getResultList();
            mostrar(usuarioList);

            //16. Uso de criteriaUpdate
            /*
            Tomar en cuenta que esta operacion se asigna directamente a la operacion
            de actualizacion d ela base de datos. Por lo tanto el contexto de persistencia
            no esta sincronizado con el resultado
             */
            LOG.info("16. Uso de criteriaUpdate");

            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaUpdate
            criteriaUpdateUsuario = cb.createCriteriaUpdate(Usuario.class);

            //Paso3. Creamos el objeto raiz del Query
            fromUsuario = criteriaUpdateUsuario.from(Usuario.class);

            //Paso4. Se construye una consulta
            criteriaUpdateUsuario.set("username", "San Lucas")
                    .where(cb.equal(fromUsuario.get("idUsuario"), 1382));

            //Paso5. Ejecutamos la consulta
            tx = entityManager.getTransaction();
            tx.begin();

            int filasActualizadas = entityManager.createQuery(criteriaUpdateUsuario)
                    .executeUpdate();
            LOG.info("nro de filas actualizadas : {}", filasActualizadas);

            tx.commit();

            //17. Uso de criteriaDelete
            LOG.info("17. Uso de criteriaDelete");
            //Paso1. El objeto entityManager crea una instancia de CriteriaBuilder
            cb = entityManager.getCriteriaBuilder();

            //Paso2. Se crea una instancia de CriteriaDelete
            criteriaDeleteUsuario = cb.createCriteriaDelete(Usuario.class);

            //Paso3. Creamos el objeto raiz del Query
            fromUsuario = criteriaDeleteUsuario.from(Usuario.class);

            //Paso4. Se construye una consulta
            criteriaDeleteUsuario.where(cb.equal(fromUsuario.get("idUsuario"), 1382));

            //Paso5. Ejecutamos la consulta
            tx = entityManager.getTransaction();
            tx.begin();

            int filasEliminadas = entityManager.createQuery(criteriaDeleteUsuario).executeUpdate();
            LOG.info("numero de filas eliminadas : {}", filasEliminadas);

            tx.commit();

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
