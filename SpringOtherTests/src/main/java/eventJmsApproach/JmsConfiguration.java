package eventJmsApproach;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@EnableJms // Jest niezb�dne, aby dzia�ay annotacje @JmsListener
public class JmsConfiguration implements JmsListenerConfigurer
{
  @Autowired
  ConnectionFactory connectionFactory;
  @Autowired
  DefaultJmsListenerContainerFactoryConfigurer configurer;

  // Tworzenie customowego ListenerContainerFactory.
  // Wymaga implements JmsListenerConfigurer
  @Override
  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar)
  {
    registrar.setContainerFactory(containerFactory());
  }

  @Bean
  public JmsListenerContainerFactory<?> containerFactory()
  {
    // Tworzymy defaultowy ListenerContainerFactory
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();    
    configurer.configure(factory, connectionFactory);
    // Nadpisujemy wymagane dane
    
    // Ustawienie customowej obs�ugi b�ed�w. JmsErrorHandler implements org.springframework.util.ErrorHandler
    //factory.setErrorHandler(new JmsErrorHandler()); 
    
    // Customowe ustawienia Back-off, czyli po jakim czasie JMS ponownie 
    // spr�buje odwo�a� si� do brokera je�li ten nie odpowiada.
    //factory.setBackOff(new ExponentialBackOff());
    return factory;
  }

  // NIEZB�DNE do przekazywania obiekt�w innych ni� serializable
  // Tworzymy konwerter do serializacji na bezie json, aby przekazywa� obiekty z danymi.
  // Obowi�zuj� zasady konwersji jackson (adnotacje etc)
  // Niewiele rozumiem
  @Bean
  public MessageConverter jacksonJmsMessageConverter1()
  {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }
}
