import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PensionDetailsComponent } from './pension-details.component';

describe('PensionDetailsComponent', () => {
  let component: PensionDetailsComponent;
  let fixture: ComponentFixture<PensionDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PensionDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PensionDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
