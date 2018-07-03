package cn.lwj;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Author: 李维健
 * @Date: 2018\7\3 0003
 */
@Mojo(name = "count", defaultPhase = LifecyclePhase.COMPILE)
public class Count extends AbstractMojo {

    // 扫描路径
    @Parameter
    private String path;

    // 需要统计的文件后缀
    @Parameter
    private List<String> suffexs = new ArrayList<>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<String> fileNames = scanFiles(path);
        int count = 0;
        if (null == null || fileNames.equals("")) {
            System.out.println("没有文件");
        } else {
            for (String suffex : suffexs) {
                for (int i = 0; i < fileNames.size(); i++) {
                    if (fileNames.get(i).endsWith(suffex)) {
                        count++;
                    }
                    if (i == fileNames.size()) {
                        System.out.println(suffex + "：" + count);
                        count = 0;
                    }
                }
            }
        }
    }

    private List<String> scanFiles(String path) {
        List<String> fileNames = new ArrayList<>();

        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    scanFiles(file1.getAbsolutePath());
                } else {
                    fileNames.add(file1.getName());
                }
            }
        }

        return fileNames;
    }
}
