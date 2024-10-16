package top.rayc.personalsite.utility.config.mybatis

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import java.time.LocalDateTime

class DateTimeMetaObjectHandler: MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createAt", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "updateAt", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "publishAt", LocalDateTime::class.java, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updateAt", LocalDateTime::class.java, LocalDateTime.now())
        this.strictUpdateFill(metaObject, "publishAt", LocalDateTime::class.java, LocalDateTime.now())
    }

}