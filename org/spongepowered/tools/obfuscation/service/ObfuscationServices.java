package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

public final class ObfuscationServices {
  private final ServiceLoader<IObfuscationService> serviceLoader;
  
  private static ObfuscationServices instance;
  
  private final Set<IObfuscationService> services = new HashSet<IObfuscationService>();
  
  private ObfuscationServices() {
    this.serviceLoader = ServiceLoader.load(IObfuscationService.class, getClass().getClassLoader());
  }
  
  public static ObfuscationServices getInstance() {
    if (instance == null)
      instance = new ObfuscationServices(); 
    return instance;
  }
  
  public void initProviders(IMixinAnnotationProcessor paramIMixinAnnotationProcessor) {
    try {
      for (IObfuscationService iObfuscationService : this.serviceLoader) {
        if (!this.services.contains(iObfuscationService)) {
          this.services.add(iObfuscationService);
          String str = iObfuscationService.getClass().getSimpleName();
          Collection<ObfuscationTypeDescriptor> collection = iObfuscationService.getObfuscationTypes();
          if (collection != null)
            for (ObfuscationTypeDescriptor obfuscationTypeDescriptor : collection) {
              try {
                ObfuscationType obfuscationType = ObfuscationType.create(obfuscationTypeDescriptor, paramIMixinAnnotationProcessor);
                paramIMixinAnnotationProcessor.printMessage(Diagnostic.Kind.NOTE, str + " supports type: \"" + obfuscationType + "\"");
              } catch (Exception exception) {
                exception.printStackTrace();
              } 
            }  
        } 
      } 
    } catch (ServiceConfigurationError serviceConfigurationError) {
      paramIMixinAnnotationProcessor.printMessage(Diagnostic.Kind.ERROR, serviceConfigurationError.getClass().getSimpleName() + ": " + serviceConfigurationError.getMessage());
      serviceConfigurationError.printStackTrace();
    } 
  }
  
  public Set<String> getSupportedOptions() {
    HashSet<String> hashSet = new HashSet();
    for (IObfuscationService iObfuscationService : this.serviceLoader) {
      Set<String> set = iObfuscationService.getSupportedOptions();
      if (set != null)
        hashSet.addAll(set); 
    } 
    return hashSet;
  }
  
  public IObfuscationService getService(Class<? extends IObfuscationService> paramClass) {
    for (IObfuscationService iObfuscationService : this.serviceLoader) {
      if (paramClass.getName().equals(iObfuscationService.getClass().getName()))
        return iObfuscationService; 
    } 
    return null;
  }
}
