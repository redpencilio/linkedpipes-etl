package com.linkedpipes.etl.dpu.api;

import com.linkedpipes.etl.executor.api.v1.exception.LocalizedString;
import com.linkedpipes.etl.executor.api.v1.exception.NonRecoverableException;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * This class provide basic definition that should by new components.
 *
 * @author Petr Škoda
 */
public interface DataProcessingUnit {

    public interface Context {

        /**
         * Return true if the execution of current {@link DPU} should be stopped as soon as possible.
         *
         * @return True if the execution should stop.
         */
        public boolean canceled();

        public String getComponentUri();

        public File getWorkingDirectory();

    }

    /**
     * Base class for exception and failure reporting.
     *
     * The reference of arguments in message should by done by '{}' string.
     */
    public class ExecutionFailed extends NonRecoverableException {

        public ExecutionFailed(String message, Object... args) {
            super(Arrays.asList(new LocalizedString(message, "en")), args);
        }

    }

    public class ExecutionCancelled extends ExecutionFailed {

        public ExecutionCancelled() {
            super("Execution cancelled.");
        }

    }

    /**
     * Used to assign type to the component. Use types from {@link com.linkedpipes.dpu.basic.vocabulary.BaseTerms}.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Type {

        String[] value();

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Extension {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Configuration {

        Class<?>[] history() default {};

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface InputPort {

        String id();

        boolean optional() default false;

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface OutputPort {

        String id();

    }

}
