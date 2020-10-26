package com.entity;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class MyDept {
  private  Integer id;
  private  String pname;
  private  String premark;
  private Integer pflag;

  public MyDept(Integer id, String pname, String premark, Integer pflag) {
    this.id = id;
    this.pname = pname;
    this.premark = premark;
    this.pflag = pflag;
  }

  public MyDept() {
    super();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public String getPremark() {
    return premark;
  }

  public void setPremark(String premark) {
    this.premark = premark;
  }

  public Integer getPflag() {
    return pflag;
  }

  public void setPflag(Integer pflag) {
    this.pflag = pflag;
  }

  @Override
  public String toString() {
    return "MyDept{" +
            "id=" + id +
            ", pname='" + pname + '\'' +
            ", premark='" + premark + '\'' +
            ", pflag=" + pflag +
            '}';
  }
}
