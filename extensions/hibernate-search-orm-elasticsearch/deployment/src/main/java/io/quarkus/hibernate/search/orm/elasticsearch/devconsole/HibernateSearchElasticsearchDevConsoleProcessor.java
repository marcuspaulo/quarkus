package io.quarkus.hibernate.search.orm.elasticsearch.devconsole;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;
import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.pkg.builditem.CurateOutcomeBuildItem;
import io.quarkus.devconsole.spi.DevConsoleRouteBuildItem;
import io.quarkus.devconsole.spi.DevConsoleRuntimeTemplateInfoBuildItem;
import io.quarkus.hibernate.search.orm.elasticsearch.runtime.HibernateSearchElasticsearchRuntimeConfig;
import io.quarkus.hibernate.search.orm.elasticsearch.runtime.devconsole.HibernateSearchDevConsoleRecorder;

public class HibernateSearchElasticsearchDevConsoleProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    @Record(RUNTIME_INIT)
    public DevConsoleRuntimeTemplateInfoBuildItem collectBeanInfo(HibernateSearchDevConsoleRecorder recorder,
            HibernateSearchElasticsearchRuntimeConfig runtimeConfig,
            CurateOutcomeBuildItem curateOutcomeBuildItem) {
        return new DevConsoleRuntimeTemplateInfoBuildItem("indexedEntityTypes",
                recorder.infoSupplier(runtimeConfig), this.getClass(), curateOutcomeBuildItem);
    }

    @BuildStep
    @Record(value = STATIC_INIT, optional = true)
    DevConsoleRouteBuildItem invokeEndpoint(HibernateSearchDevConsoleRecorder recorder) {
        return new DevConsoleRouteBuildItem("entity-types", "POST", recorder.indexEntity());
    }
}
