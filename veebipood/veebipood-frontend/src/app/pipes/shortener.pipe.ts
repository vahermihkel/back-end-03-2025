import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'shortener'
})
export class ShortenerPipe implements PipeTransform {

  transform(value: string, wordLength: number): string {
    return value.length <= wordLength+2 ? value : value.substring(0,wordLength) + "...";
  }

}
