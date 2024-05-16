package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Blogpost;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.blogpost.BlogpostDTO;
import noemi.genshin_world.payloads.blogpost.BlogpostResponseDTO;
import noemi.genshin_world.services.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/blogpost")
public class BlogpostController {
    @Autowired
    private BlogpostService blogpostService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public BlogpostResponseDTO createBlogpost(@RequestBody @Validated BlogpostDTO blogpost, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Blogpost newBlogpost = blogpostService.saveBlogpost(blogpost);
            return new BlogpostResponseDTO(newBlogpost.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Blogpost> getBlogposts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "12") int size,
                                       @RequestParam(defaultValue = "id") String orderBy){
        return blogpostService.findAll(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Blogpost getBlogpostById(@PathVariable UUID id){
        return blogpostService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Blogpost getBlogpostByIdAndUpdate(@PathVariable UUID id, @RequestBody BlogpostDTO body){
        return blogpostService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getBlogpostByIdAndDelete(@PathVariable UUID id){
        blogpostService.findByIdAndDelete(id);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return blogpostService.uploadImage(id, body);
    }
}
