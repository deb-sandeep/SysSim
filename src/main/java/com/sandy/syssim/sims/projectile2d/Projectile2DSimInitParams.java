package com.sandy.syssim.sims.projectile2d;

import com.sandy.syssim.core.simbase.InitParams;
import lombok.Data;

@Data
public class Projectile2DSimInitParams extends InitParams {

   private double u = 10 ;
   private double theta = 30 ;
   private double g = 9.8 ;
}
