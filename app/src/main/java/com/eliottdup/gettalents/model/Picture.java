package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

<<<<<<< HEAD
import java.io.Serializable;

=======
>>>>>>> d46e51dc00e396aa192c34c9542d929d1b2e4726
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
public class Picture implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("path")
    @Expose
    private String path;

=======
public class Picture {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("path")
    @Expose
    private String path;
>>>>>>> d46e51dc00e396aa192c34c9542d929d1b2e4726
}
