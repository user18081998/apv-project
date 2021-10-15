package org.apv.project.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VM {
    private String id;
    private String name;
    private String computerName;
    private String os;
    private String size;
    private String status;
//    private String osProfile;
}
