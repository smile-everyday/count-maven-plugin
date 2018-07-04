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
 * @Title: maven 统计项目下各文件类型数量
 * @Author: Dark Souls
 * @Date: 2018\7\3 0003
 */
@Mojo(name = "count", defaultPhase = LifecyclePhase.COMPILE)
public class Count extends AbstractMojo{

    // 扫描路径
    @Parameter
    private String route;

    // 需要统计的文件后缀
    @Parameter
    private List<String> suffexs = new ArrayList<>();

    /**
     * 存储文件名
     */
    private List<String> fileNames = new ArrayList<>();

    /**
     * 文件个数
     */
    private int count = 0;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        scanFiles(route);

        if (null == fileNames || fileNames.size() == 0) {
            System.out.println("No files!!!");
        } else {
            for (String suffex : suffexs) {
                for (int i = 0; i < fileNames.size(); i++) {
                    if (fileNames.get(i).endsWith(suffex)) {
                        count++;
                    }
                    if (i == fileNames.size() - 1) {
                        System.out.println(suffex + "：" + count);
                        count = 0;
                    }
                }
            }
        }
    }

    private void scanFiles(String path) {
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

    }

}
