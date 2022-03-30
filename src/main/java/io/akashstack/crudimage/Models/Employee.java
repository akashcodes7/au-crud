package io.akashstack.crudimage.Models;
import javax.persistence.*;

@Entity
@Table(name = "image_table")
public class Employee {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "picByte", length = 100000)
	private byte[] picByte;

	@Column(name = "picType")
	private String picType;

	@Column(name = "picName")
	private String picName;
	
	public Employee(){}
	public Employee(String name, String email, String picName, String picType, byte[] picByte) {
		super();
		this.name = name;
		this.email = email;
		this.picByte = picByte;
		this.picType = picType;
		this.picName = picName;
	}

	public String GetpicName() {
		return name;
	}
	public void SetpicName(String name) {
		this.picName = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

   

}