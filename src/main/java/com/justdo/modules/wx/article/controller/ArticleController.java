package com.justdo.modules.wx.article.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.article.entity.Article;
import com.justdo.modules.wx.article.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 文章管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/2/29 下午9:07
 */
@RestController
@RequestMapping("/wx/article")
public class ArticleController extends AbstractController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章列表
     */
    @Log("文章列表")
    @GetMapping("/list")
    @RequiresPermissions("wx:article:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = articleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 文章详情
     */
    @Log("文章详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:article:info")
    public R info(@PathVariable("id") Integer id){

		Article article = articleService.getById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存文章
     */
    @Log("文章详情")
    @PostMapping("/save")
    @RequiresPermissions("wx:article:save")
    public R save(@RequestBody Article article){

		articleService.save(article);

        return R.ok();
    }

    /**
     * 修改文章
     */
    @Log("修改文章")
    @PostMapping("/update")
    @RequiresPermissions("wx:article:update")
    public R update(@RequestBody Article article){

		articleService.updateById(article);

        return R.ok();
    }

    /**
     * 删除文章
     */
    @Log("删除文章")
    @PostMapping("/delete")
    @RequiresPermissions("wx:article:delete")
    public R delete(@RequestBody Integer[] ids){

		articleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
