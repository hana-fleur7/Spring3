package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.Employee;

@Repository
public class EmployeeRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setAge(rs.getInt("age"));
        employee.setGender(rs.getString("gender"));
        employee.setDepartmentId(rs.getInt("department_id"));
        return employee;
    };

    public Employee load(Integer id){  //主キー検索を行うメソッド
        String sql = """
                SELECT id
                ,name
                ,age
                ,gender
                ,department_id FROM employees WHERE id = :id";
                
                 """;

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

    }

    

    public List<Employee> findAll(){  //全件検索を行う
        String sql = """
                SELECT id
                ,name
                ,age
                ,gender
                ,department_id FROM employees ORDER BY age
                """;
        return template.query(sql, EMPLOYEE_ROW_MAPPER);
       

    }

    public Employee save(Employee employee){  //渡した従業員情報を保存または更新する
        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

        if(employee.getId() == null){
            String insertSql = "INSERT INTO employees(name,age,gender,department_id)"
            + "VALUES(:name, :age, :gender, :departmentId)";
            template.update(insertSql, param);
        }else{
            String updateSql ="UPDATE employee SET name = :name, age = :age"
            + "gender = :gender, department_id = :departmentId" + "WHERE id = :id";
            template.update(updateSql, param);
        }
        return employee;
    }

    public void deleteById(Integer id){ //主キーを使って一件の従業員情報を削除する
     
        String deleteSql = 
                "DERETE FROM employees WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        
        template.update(deleteSql,param);
    }
}
