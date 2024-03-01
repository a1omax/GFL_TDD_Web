package gfl.tdd_web.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorData {
    private double startInterval = 1.0;
    private double endInterval = 5.0;
    private double step = 0.01;
    private double a = 2.4;
}
