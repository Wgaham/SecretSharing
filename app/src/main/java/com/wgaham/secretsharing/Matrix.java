package com.wgaham.secretsharing;

/**
 * 矩阵的支持类
 *
 * @author Simba
 */
class Matrix {
    // 矩阵的计算
    double determinant(double[][] yuzs2) {
        double result2 = 0;
        if (yuzs2.length > 2) {
            // 每次选择第一行展开
            for (int i = 0; i < yuzs2[0].length; i++) {
                // 系数符号
                double f = Math.pow(-1, i);
                // 求余子式
                double[][] yuzs = new double[yuzs2.length - 1][yuzs2[0].length - 1];
                for (int j = 0; j < yuzs.length; j++) {
                    for (int j2 = 0; j2 < yuzs[0].length; j2++) {
                        // 去掉第一行，第i列之后的行列式即为余子式
                        if (j2 < i) {
                            yuzs[j][j2] = yuzs2[j + 1][j2];
                        } else {
                            yuzs[j][j2] = yuzs2[j + 1][j2 + 1];
                        }

                    }
                }
                // 行列式的拉普拉斯展开式，递归计算
                result2 += yuzs2[0][i] * determinant(yuzs) * f;
            }
        } else {
            // 两行两列的行列式使用公式
            if (yuzs2.length == 2) {
                result2 = yuzs2[0][0] * yuzs2[1][1] - yuzs2[0][1] * yuzs2[1][0];
            }
            // 单行行列式的值即为本身
            else {
                result2 = yuzs2[0][0];
            }
        }
        return result2;
    }


}