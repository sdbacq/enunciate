package net.sf.enunciate.modules;

import net.sf.enunciate.main.Enunciate;

import java.io.IOException;

/**
 * Basic stub for a deployment module.  Provides methods for each step.
 *
 * @author Ryan Heaton
 */
public class BasicDeploymentModule implements DeploymentModule {

  protected Enunciate enunciate;

  /**
   * Sets the enunciate mechanism.
   *
   * @param enunciate The enunciate mechanism.
   */
  public void init(Enunciate enunciate) {
    this.enunciate = enunciate;
  }

  /**
   * The enunciate mechanism.
   *
   * @return The enunciate mechanism.
   */
  public Enunciate getEnunciate() {
    return enunciate;
  }

  /**
   * Calls the step methods as necessary.
   *
   * @param target The step.
   */
  public void step(Enunciate.Target target) throws IOException {
    switch (target) {
      case GENERATE:
        doGenerate();
        break;
      case BUILD:
        doBuild();
        break;
      case COMPILE:
        doCompile();
        break;
      case PACKAGE:
        doPackage();
        break;
    }
  }

  /**
   * Default implementation is a no-op.
   */
  protected void doGenerate() throws IOException {
  }

  /**
   * Default implementation is a no-op.
   */
  protected void doBuild() throws IOException {
  }

  /**
   * Default implementation is a no-op.
   */
  protected void doCompile() throws IOException {
  }

  /**
   * Default implementation is a no-op.
   */
  protected void doPackage() throws IOException {
  }

  /**
   * Default implementation is a no-op.
   */
  public void close() {
  }

}