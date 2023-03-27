package com.opensourcewithslu.utilities;

import com.opensourcewithslu.inputdevices.RotaryEncoderHelper;
import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalInputMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultiPinConfigs.PwmMultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class Pi4JMultipinFactory {

    @EachBean(DigitalInputMultiPinConfiguration.class)
    public MultipinConfiguration multiPinInput(DigitalInputMultiPinConfiguration config, Context pi4jContext){
        int[] addresses = config.getAddresses();
        DigitalInput[] allInputs = new DigitalInput[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)
                    .id(config.getId() + String.valueOf(i))
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .debounce(config.getDebounces()[i])
                    .pull(config.getPulls()[i])
                    .provider(config.getProvider());

            allInputs[i] = pi4jContext.create(inputConfigBuilder);
        }

        return new MultipinConfiguration(config.getId(), allInputs);
    }

    @EachBean(PwmMultiPinConfiguration.class)
    public MultipinConfiguration multiPinPwm(PwmMultiPinConfiguration config, Context pi4jContext){
        int[] addresses = config.getAddresses();
        Pwm[] allPwms = new Pwm[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var pwmConfigBuilder = Pwm.newConfigBuilder(pi4jContext)
                    .id(config.getId() + String.valueOf(i))
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .pwmType(config.getPwmTypes()[i])
                    .initial(config.getInitals()[i])
                    .shutdown(config.getShutdowns()[i])
                    .provider(config.getProvider());

            allPwms[i] = pi4jContext.create(pwmConfigBuilder);
        }

        return new MultipinConfiguration(config.getId(), allPwms);
    }
}
