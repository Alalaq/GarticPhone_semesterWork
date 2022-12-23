package ru.kpfu.itis.general.helpers.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrawingCode {
    private int round;
    private Long playerId;
    private boolean isUsed;
}
