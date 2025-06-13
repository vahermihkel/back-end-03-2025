import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProduct3Component } from './add-product-3.component';

describe('AddProduct3Component', () => {
  let component: AddProduct3Component;
  let fixture: ComponentFixture<AddProduct3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddProduct3Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddProduct3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
