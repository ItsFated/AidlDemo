package com.im_hero.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Jason
 * @version 1.0
 */

public class Employee implements Parcelable {
    private String name;
    private short age;

    public Employee() {
    }

    public Employee(String name, short age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        age = (short) dest.readInt();
    }

    protected Employee(Parcel in) {
        this.name = in.readString();
        this.age = (short) in.readInt();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}
