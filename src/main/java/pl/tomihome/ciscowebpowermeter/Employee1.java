package pl.tomihome.ciscowebpowermeter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "COMPANY",
		uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Employee1 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, length = 11)
	private int id;
	
	@Column(name = "NAME", nullable = false, length = 20)
	private String name;
	
	@Column(name = "AGE", nullable = false, length = 20)
	private int age;
	
	@Column(name = "ADDRESS", nullable = false, length = 20)
	private String address;
	
	@Column(name = "SALARY", nullable = false, length = 20)
	private int salary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}

