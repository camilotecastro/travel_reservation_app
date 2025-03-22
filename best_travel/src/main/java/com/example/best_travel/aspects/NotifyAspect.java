package com.example.best_travel.aspects;

import com.example.best_travel.util.BestTravelUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NotifyAspect {

  private static final String LINE_FORMAT = "At, %s, new request with size page %s and order %s";

  @After(value = "@annotation(com.example.best_travel.util.annotations.Notify)")
  public void notifyInFile(JoinPoint joinPoint) throws IOException {
    var args = joinPoint.getArgs();
    var size = args[0];
    var order = args[2] == null ? "NONE" : args[2];
    var text = String.format(LINE_FORMAT, LocalDateTime.now(), size.toString(), order);

    var signature = (MethodSignature)joinPoint.getSignature();
    var method = signature.getMethod();
    var annotation = method.getAnnotation(com.example.best_travel.util.annotations.Notify.class);
    var path = annotation.value();

    BestTravelUtil.writeNotification(text, path);
  }

}
