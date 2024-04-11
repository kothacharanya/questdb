package io.questdb.test;

import io.questdb.DirWatcher;
import io.questdb.DirWatcherCallback;
import io.questdb.DirWatcherFactory;
import io.questdb.mp.SOCountDownLatch;
import io.questdb.std.Files;
import io.questdb.std.Os;
import io.questdb.std.str.Path;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class DirWatcherTest extends AbstractTest {

    @Test
    public void testDirWatcher() throws Exception {

        final File targetFile = temp.newFile();

        try (final DirWatcher dw = DirWatcherFactory.getDirWatcher(temp.getRoot().getAbsolutePath())) {
            Assert.assertNotNull(dw);
            FileChangedCallback callback = new FileChangedCallback(targetFile.getAbsolutePath());
            SOCountDownLatch threadLatch = new SOCountDownLatch(1);

            Thread thread = new Thread(() -> {
                try {
                    do {
                        // todo: exit strategy
                        dw.waitForChange(callback);
                    } while (true);
                } finally {
                    threadLatch.countDown();
                }
            });
            thread.start();

            try (PrintWriter writer = new PrintWriter(targetFile.getAbsolutePath(), StandardCharsets.UTF_8)) {
                writer.println("hello");
            }

            Thread.sleep(10);

            Assert.assertTrue(callback.pollChanged());
            Assert.assertFalse(callback.pollChanged());

            try (PrintWriter writer = new PrintWriter(targetFile.getAbsolutePath(), StandardCharsets.UTF_8)) {
                writer.println("hello again");
            }

            Thread.sleep(10);

            Assert.assertTrue(callback.pollChanged());

            threadLatch.await();
        }
    }

    static class FileChangedCallback implements DirWatcherCallback {

        boolean changed;
        String fp;
        long lastModified;

        public FileChangedCallback(String fp) {
            this.fp = fp;
            try (Path p = new Path()) {
                p.of(this.fp);
                this.lastModified = Files.getLastModified(p);
            }
        }

        @Override
        public void onDirChanged() {
            try (Path p = new Path()) {
                p.of(this.fp);
                long lastModified = Files.getLastModified(p);
                if (lastModified > this.lastModified) {
                    this.lastModified = lastModified;
                    this.changed = true;
                }
            }
        }

        public boolean pollChanged() {
            if (changed) {
                changed = false;
                return true;
            }
            return false;

        }
    }

    static {
        Os.init();
    }
}