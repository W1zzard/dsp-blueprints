package com.azhiltsov.api;

import com.azhiltsov.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ItemRepository itemRepository;


}
