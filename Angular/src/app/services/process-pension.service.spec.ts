import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ProcessPensionService } from './process-pension.service';

describe('ProcessPensionService', () => {
  let service: ProcessPensionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
    });
    service = TestBed.inject(ProcessPensionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
