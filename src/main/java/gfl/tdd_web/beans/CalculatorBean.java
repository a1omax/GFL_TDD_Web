package gfl.tdd_web.beans;

import gfl.tdd_web.data.CalculatorData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@Named
@RequestScoped
public class CalculatorBean implements Serializable {


    private CalculatorData calculatorData = new CalculatorData();

    public String calculate(){
        return "calculationResult";
    }

}
