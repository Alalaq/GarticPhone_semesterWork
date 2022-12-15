package ru.kpfu.itis.general.entities;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drawing {
    protected String image;
    protected Player author; //maybe just set String for this one???
}
