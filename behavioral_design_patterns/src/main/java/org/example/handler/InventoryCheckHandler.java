
package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class InventoryCheckHandler extends OrderValidationHandler {

    @Autowired
    ItemsRepository itemsRepository;

    @Override
    public void validate(Order order) {


        Optional<Item> item = itemsRepository.findById(order.getItemId());
        if (item.isPresent()) {
            if(item.get().getItemAvailability().equals("available")) {
                log.info("Item {} is available", order.getItemId());
                super.validate(order);
            } else {
                log.info("Item {} is not available", order.getItemId());
            }
        }else{
            log.error("Item {} id could not be found", order.getItemId());
            throw new RuntimeException("Item not found");
        }

    }
}
